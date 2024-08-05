package main

import (
	"net/http"
	"strconv"
	"words-service/config"
	"words-service/database"

	"github.com/gin-contrib/cors"
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
)

func main() {
	cfg, err := config.LoadConfig()
	if err != nil {
		panic("Failed to load config!" + err.Error())
	}

	if err := database.ConnectDatabase(cfg); err != nil {
		panic("Failed to connect to database" + err.Error())
	}

	if err := database.DB.Migrator().DropTable(&database.Words{}); err != nil {
		panic("Failed to drop old table: " + err.Error())
	}

	err = database.DB.AutoMigrate(&database.Words{})
	if err != nil {
		panic("Failed to migrate database: " + err.Error())
	}

	database.SeedDataIntoDB(database.DB)
	r := gin.Default()

	r.Use(cors.New(cors.Config{
		AllowOrigins:     []string{"http://localhost:8010"},
		AllowMethods:     []string{"GET", "POST", "PUT", "DELETE", "OPTIONS"},
		AllowHeaders:     []string{"Origin", "Content-Type", "Accept"},
		AllowCredentials: true,
	}))

	r.GET("/words", GetWords)
	r.GET("/words/:id", GetWordById)
	r.POST("/words", CreateWords)
	r.DELETE("/words/:id", DeleteWord)

	r.Run(":8030")
}

func GetWords(c *gin.Context) {
	var allWords []database.Words

	if err := database.DB.Find(&allWords).Error; err != nil {
		c.JSON(500, gin.H{"error": err.Error()})
		return
	}
	c.JSON(200, allWords)
}

func GetWordById(c *gin.Context) {
	idStr := c.Param("id")
	id, err := strconv.Atoi(idStr)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid ID format"})
		return
	}

	var word database.Words
	if err := database.DB.First(&word, id).Error; err != nil {
		if err == gorm.ErrRecordNotFound {
			c.JSON(http.StatusNotFound, gin.H{"error": "Word not found"})
		} else {
			c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		}
		return
	}

	c.JSON(200, word)
}

func DeleteWord(c *gin.Context) {
	id := c.Param("id")

	if err := database.DB.Delete(&database.Words{}, id).Error; err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to delete word"})
		return
	}

	c.JSON(http.StatusNoContent, nil)
}

func CreateWords(c *gin.Context) {
	var input database.Words

	if err := c.ShouldBindJSON(&input); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	if err := database.DB.Create(&input).Error; err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Failed to create word group"})
		return
	}
	c.JSON(201, input)
}
