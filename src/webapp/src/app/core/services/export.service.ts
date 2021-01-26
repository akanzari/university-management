import { Injectable } from '@angular/core';
import * as FileSaver from 'file-saver';
import * as XLSX from 'xlsx';
import { DatePipe } from '@angular/common';
import * as _ from 'lodash';
const CSV_EXTENSION = '.csv';
const CSV_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';

@Injectable()
export class ExcelService {

    public exportAsExcelFile(json: any): void {
        const headerFiche: XLSX.WorkSheet = XLSX.utils.json_to_sheet([
            {
                A: "Classe", B: "Session", C: "Date d'examen", D: "Heure d'examen", E: "Durée d'examen", F: "Module", G: "Surveillance", H: "Salles"
            }
        ], { header: ["A", "B", "C", "D", "E", "F", "G", "H"], skipHeader: true });

        const worksheetFiche: XLSX.WorkSheet = XLSX.utils.sheet_add_json(headerFiche, this.getExams(json),
            { header: ["A", "B", "C", "D", "E", "F", "G", "H"], skipHeader: true, origin: -1 });

        const csv = XLSX.utils.sheet_to_csv(worksheetFiche, { FS: ';' });
        const new_csv = '\uFEFF' + csv;
        const data: Blob = new Blob([new_csv], { type: CSV_TYPE });
        var datePipe = new DatePipe("en_US");
        let formattedDt = datePipe.transform(new Date(), 'dd-MM-yyyy_HH-mm-ss');
        console.log(json);
        
        FileSaver.saveAs(data, "Exams_" + formattedDt + CSV_EXTENSION);
    }

    private getExams(exams): any {
        let result: any[] = [];
        exams.forEach(exam => {
            if (exam.classes && exam.classes.length > 0) {
                exam.classes.forEach(element => {
                    let session: string;
                    if (exam.session === "S1P1" || exam.session === "S2P1") {
                        session = exam.session + " - SD"
                    } else if (exam.session === "S1P2" || exam.session === "S2P2" || exam.session === "SP") {
                        session = exam.session + " - Examen"
                    } else if (exam.session === "SR") {
                        session = exam.session + " - Rattrapage"
                    }
                    result.push({
                        B: session,
                        A: element.classId,
                        C: element.examDate,
                        D: element.examHour.toString().substring(0, 2) + "H:" + element.examHour.toString().substring(2, 4),
                        E: element.examDuration + " min",
                        F: element.module.designation,
                        G: element.teachers.map(teacher => teacher.fullName).toString(),
                        H: element.rooms.map(room => room.classRoomId).toString()
                    });
                });
            }
        })
        return result;
    }

}