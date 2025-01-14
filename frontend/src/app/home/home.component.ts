import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { WeatherService } from '../services/weather.service';
import { WeatherData } from '../interfaces/weather.interface';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FormsModule, CommonModule],
  providers: [WeatherService],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  cityName: string = '';
  weatherData: WeatherData | null = null;
  cities: string[] = [];
  filteredCities: string[] = [];
  showSuggestions: boolean = false;

  constructor(private weatherService: WeatherService) {}

  ngOnInit() {
    this.loadCities();
  }

  loadCities() {
    this.weatherService.getAllCities().subscribe({
      next: (cities) => {
        this.cities = cities;
        this.filteredCities = cities;
      },
      error: (error) => console.error('Error loading cities:', error)
    });
  }

  onInputChange() {
    this.showSuggestions = true;
    this.filteredCities = this.cities.filter(city => 
      city.toLowerCase().includes(this.cityName.toLowerCase())
    );
  }

  selectCity(city: string) {
    this.cityName = city;
    this.showSuggestions = false;
  }

  onSubmit() {
    if (this.cityName) {
      this.weatherService.getWeatherByCity(this.cityName)
        .subscribe({
          next: (data) => {
            this.weatherData = data;
            console.log('Weather data:', data);
          },
          error: (error) => {
            console.error('Error fetching weather data:', error);
          }
        });
    }
  }
}
