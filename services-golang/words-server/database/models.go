package database

import (
	"github.com/lib/pq"
	"gorm.io/gorm"
)

type Words struct {
	gorm.Model
	Group               int `gorm:"unique"`
	Name                string
	ListOfArmenianWords pq.StringArray `gorm:"type:text[]"`
	ListOfRussianWords  pq.StringArray `gorm:"type:text[]"`
}
