package config

import (
	"github.com/spf13/viper"
)

type Config struct {
	Database struct {
		User     string `mapstructure:"user"`
		Password string `mapstructure:"password"`
		Host     string `mapstructure:"host"`
		Port     int    `mapstructure:"port"`
		Name     string `mapstructure:"name"`
	}
}

func LoadConfig() (Config, error) {
	var config Config
	viper.SetConfigFile("config.yaml")
	viper.AutomaticEnv()

	if err := viper.ReadInConfig(); err != nil {
		return config, err
	}

	err := viper.Unmarshal(&config)
	return config, err
}
