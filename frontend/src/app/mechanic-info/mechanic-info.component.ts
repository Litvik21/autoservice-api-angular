import { Component, OnInit } from '@angular/core';
import { Mechanic } from '../model/mechanic';
import { MechanicService } from '../service/mechanic.service';
import { Router } from '@angular/router';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-mechanic-info',
  templateUrl: './mechanic-info.component.html',
  styleUrls: ['./mechanic-info.component.scss']
})
export class MechanicInfoComponent implements OnInit {
  mechanics: Mechanic[] = []
  constructor(private mechanicService: MechanicService,
              private router: Router) { }

  ngOnInit(): void {
    this.getMechanics();
  }
  getMechanics(): void {
    this.mechanicService.getMechanics()
      .subscribe(mechanics => this.mechanics = mechanics);
  }
  update(mechanicId: any): void {
    this.router.navigate(['/mechanics', mechanicId]);
  }

  getSalary(mechanicId: any): void {
    this.router.navigate(['/mechanics/salary', mechanicId]);
  }

  getFinishedOrders(mechanicId: any): void {
    console.log(mechanicId)
    this.router.navigate(['/mechanics', mechanicId,'finished-orders']);
  }

  downloadSalaryReport() {
    this.mechanicService.getSalaryReport().subscribe(response => {
      console.log(response.salaryReport);
      // Декодируем Base64 строку в бинарные данные
      const binaryData = atob(response.salaryReport);

      // Преобразуем бинарные данные в Uint8Array
      const byteArray = new Uint8Array(binaryData.length);
      for (let i = 0; i < binaryData.length; i++) {
        byteArray[i] = binaryData.charCodeAt(i);
      }

      // Создаём Blob с нужным типом MIME
      const blob = new Blob([byteArray], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

      // Скачиваем файл
      const url = window.URL.createObjectURL(blob);
      const anchor = document.createElement('a');
      anchor.href = url;
      anchor.download = 'salary-report.xlsx'; // Имя файла
      anchor.click();
      window.URL.revokeObjectURL(url); // Освобождение ресурсов
    });
  }

}
