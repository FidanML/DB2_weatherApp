import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
  deps: [HttpClient]
})
export class WeatherService {
  private apiUrl = 'http://localhost:8080/api/weather'; // updated base URL to include /api

  constructor(private http: HttpClient) { }

  getWeatherByCity(city: string): Observable<any> {
    const encodedCity = encodeURIComponent(city);
    return this.http.get(`${this.apiUrl}/${encodedCity}`);
  }

  getAllCities(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/cities`);
  }
}
