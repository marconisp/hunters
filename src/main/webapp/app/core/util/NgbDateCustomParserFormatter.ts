import { NgbDateParserFormatter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { Injectable } from '@angular/core';

@Injectable()
export class NgbDateCustomParserFormatter extends NgbDateParserFormatter {
  parse(value: string): any {
    if (value) {
      const dateParts = value.trim().split('-');
      if (dateParts.length === 1) {
        return { day: dateParts[0], month: 0, year: 0 };
      } else if (dateParts.length === 2) {
        return {
          day: dateParts[0],
          month: dateParts[1],
          year: null,
        };
      } else if (dateParts.length === 3) {
        return {
          day: dateParts[0],
          month: dateParts[1],
          year: dateParts[2],
        };
      }
    }
    return null;
  }

  format(date: NgbDateStruct): string {
    return `${date.year}-${date.month}-${date.day}`;
  }
}
