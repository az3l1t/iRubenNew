package database

import (
	"fmt"
	"words-service/config"

	"github.com/lib/pq"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

var DB *gorm.DB

func ConnectDatabase(cfg config.Config) error {
	dsn := fmt.Sprintf("host=%s port=%d user=%s password=%s dbname=%s sslmode=disable",
		cfg.Database.Host, cfg.Database.Port, cfg.Database.User, cfg.Database.Password, cfg.Database.Name)
	var err error
	DB, err = gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		return err
	}
	return nil
}

func SeedDataIntoDB(DB *gorm.DB) {
	var count int64
	DB.Model(&Words{}).Count(&count)
	if count > 0 {
		return
	}

	seedData := []Words{
		{
			Group:               1,
			Name:                "Еда",
			ListOfArmenianWords: pq.StringArray{"Խնձոր", "Նարինջ", "Հաց", "Կարտոֆիլ", "Միս", "Կաթ", "Բատաթա", "Սատչա", "Աչուկ", "Նուշ", "Ախոր", "Կարմիր պղպեղ", "Սեխ", "Խաշար", "Երգուշ", "Պանր", "Մրգի", "Թան", "Ելակ", "Բանջարեղեն"},
			ListOfRussianWords:  pq.StringArray{"Яблоко", "Апельсин", "Хлеб", "Картофель", "Мясо", "Молоко", "Сладкий картофель", "Свекла", "Чеснок", "Орех", "Мед", "Перец", "Арбуз", "Сыр", "Йогурт", "Шоколад", "Кефир", "Ягоды", "Овощи", "Морковь"},
		},
		{
			Group:               2,
			Name:                "Мебель",
			ListOfArmenianWords: pq.StringArray{"Սեղան", "Ագար", "Անիվ", "Ներս", "Գահ", "Տեղավորություն", "Կարճ", "Սպասք", "Շեմ", "Դրվածք", "Գրադարանը", "Բարձ", "Խոհանոց", "Խորշ", "Աշխարհ", "Պատ", "Ճանապարհ", "Կիրակի", "Գլուխ", "Տվեր"},
			ListOfRussianWords:  pq.StringArray{"Стол", "Кресло", "Диван", "Шкаф", "Стул", "Комод", "Полка", "Письменный стол", "Кроватка", "Скамейка", "Зеркало", "Лампа", "Ковер", "Тумбочка", "Шторка", "Пуф", "Вешалка", "Тумба", "Книжный шкаф", "Полка"},
		},
		{
			Group:               3,
			Name:                "Цвета",
			ListOfArmenianWords: pq.StringArray{"Կարմիր", "Կանաչ", "Նարնջագույն", "Գույն", "Սպիտակ", "Դեղին", "Կապույտ", "Սև", "Բեժ", "Վարդագույն", "Գլխացավ", "Բալար", "Կիրակի", "Գուշակ", "Կանաչ", "Թթվասեր", "Մուշկ", "Բուրդ", "Նարնջ", "Սպիտակ"},
			ListOfRussianWords:  pq.StringArray{"Красный", "Зеленый", "Оранжевый", "Цвет", "Белый", "Желтый", "Синий", "Черный", "Бежевый", "Розовый", "Фиолетовый", "Серый", "Коралловый", "Бирюзовый", "Малиновый", "Голубой", "Лиловый", "Синий", "Персиковый", "Хаки"},
		},
		{
			Group:               4,
			Name:                "Числа",
			ListOfArmenianWords: pq.StringArray{"Մեկ", "Երկու", "Երեք", "Չորս", "Հինգ", "Վեց", "Յոթ", "Ութ", "Ինը", "Տաս", "Տասնմեկ", "Տասներկու", "Տասներեք", "Տասնհինգ", "Տասնվեց", "Տասնյոթ", "Տասներեք", "Տասնութ", "Տասնինը", "Մասնագի", "Երեք տաս"},
			ListOfRussianWords:  pq.StringArray{"Один", "Два", "Три", "Четыре", "Пять", "Шесть", "Семь", "Восемь", "Девять", "Десять", "Одиннадцать", "Двенадцать", "Тринадцать", "Четырнадцать", "Пятнадцать", "Шестнадцать", "Семнадцать", "Восемнадцать", "Девятнадцать", "Двадцать"},
		},
		{
			Group:               5,
			Name:                "Буквы",
			ListOfArmenianWords: pq.StringArray{"Ա", "Բ", "Գ", "Դ", "Ե", "Զ", "Է", "Ը", "Թ", "Ժ", "Ի", "Լ", "Խ", "Ծ", "Կ", "Հ", "Ձ", "Ղ", "Ճ", "Մ", "Յ", "Ն", "Շ", "Ո", "Չ", "Պ", "Ջ", "Ռ", "Ս", "Վ", "Տ", "Ր", "Ց", "Փ", "Ք", "և", "Օ", "Ֆ", "Ն"},
			ListOfRussianWords:  pq.StringArray{"А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "Й", "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ъ", "Ы", "Ь", "Э", "Ю", "Я"},
		},
	}

	for _, entry := range seedData {
		if err := DB.Create(&entry).Error; err != nil {
			panic("Failed to seed initial data: " + err.Error())
		}
	}
}
