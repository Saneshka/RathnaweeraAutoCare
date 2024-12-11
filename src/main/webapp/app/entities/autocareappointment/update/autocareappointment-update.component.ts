import { Component, inject, NgModule, OnInit, ViewEncapsulation } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { forkJoin, Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAutocareappointment } from '../autocareappointment.model';
import { AutocareappointmentService } from '../service/autocareappointment.service';
import { AutocareappointmentFormService, AutocareappointmentFormGroup } from './autocareappointment-form.service';
import { AutocareappointmenttypeService } from 'app/entities/autocareappointmenttype/service/autocareappointmenttype.service';
import { IAutocareappointmenttype } from 'app/entities/autocareappointmenttype/autocareappointmenttype.model';
import dayjs from 'dayjs/esm';
import { ICustomervehicle } from 'app/entities/customervehicle/customervehicle.model';
import { CustomervehicleService } from 'app/entities/customervehicle/service/customervehicle.service';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { AutocarehoistService } from 'app/entities/autocarehoist/service/autocarehoist.service';
import { HoisttypeService } from 'app/entities/hoisttype/service/hoisttype.service';
import { AutocaretimetableService } from 'app/entities/autocaretimetable/service/autocaretimetable.service';
import { NgbAccordionCollapse, NgbAccordionHeader, NgbAccordionModule } from '@ng-bootstrap/ng-bootstrap';
import utc from 'dayjs/plugin/utc';
@Component({
  standalone: true,
  selector: 'jhi-autocareappointment-update',
  templateUrl: './autocareappointment-update.component.html',
  encapsulation: ViewEncapsulation.None,
  imports: [SharedModule, FormsModule, ReactiveFormsModule, NgbAccordionHeader, NgbAccordionCollapse],
  styles: [
    `
      table {
        border-collapse: collapse;
        width: 100%;
      }

      th,
      td {
        padding: 10px;
        border: 1px solid #ddd;
        text-align: center;
      }
    `,
  ],
})
export class AutocareappointmentUpdateComponent implements OnInit {
  isSaving = false;
  autocareappointment: IAutocareappointment | null = null;
  autocareappointmenttypes: IAutocareappointmenttype[] = [];
  customervehicles: ICustomervehicle[] = [];
  customerDetails: any | null = null;
  selectedTime: string | null = null;
  selectedHoist: number | null = null;
  hoistData: any[] = [];
  hoistTypeData: any[] = [];
  timetableData: any[] = [];

  hoists: { id: number; name: string; times: string[] }[] = [];

  protected autocareappointmentService = inject(AutocareappointmentService);
  protected autocareappointmentFormService = inject(AutocareappointmentFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected autocareappointmenttypeService = inject(AutocareappointmenttypeService);
  protected customervehicleService = inject(CustomervehicleService);
  protected customerService = inject(CustomerService);
  protected autocarehoistService = inject(AutocarehoistService);
  protected autocarehoisttypeService = inject(HoisttypeService);
  protected autocaretimetableService = inject(AutocaretimetableService);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutocareappointmentFormGroup = this.autocareappointmentFormService.createAutocareappointmentFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autocareappointment }) => {
      this.autocareappointment = autocareappointment;
      if (autocareappointment) {
        this.updateForm(autocareappointment);
      }
      this.loadDataFromOtherEntities();
      // this.loadCustomerDetails();
      this.loadHoistAppointmentTime();
    });
  }

  getHoistsByType(hoistTypeId: number): any[] {
    return this.hoistData.filter(hoist => hoist.hoisttypeid === hoistTypeId);
  }

  getHoistTypeName(hoisttypeid: number): string | undefined {
    const hoistType = this.hoistTypeData.find(ht => ht.id === hoisttypeid);
    return hoistType ? hoistType.hoisttype : undefined;
  }

  selectTime(timetable: any): void {
    const appointmentDate = this.editForm.get('appointmentdate')?.value;
    console.log('new appointment date : ', appointmentDate);
    const dd = dayjs('1900-01-01 08:00:00', 'YYYY-MM-DD HH:mm:ss');
    console.log('check static value', dd);
    if (appointmentDate && timetable.hoisttime) {
      // Combine the date and time using dayjs
      const appointmentDateTime = dayjs(appointmentDate)
        .hour(dayjs(timetable.hoisttime).hour())
        .minute(dayjs(timetable.hoisttime).minute())
        .second(0)
        .format('YYYY-MM-DDTHH:mm'); // Use .toISOString() to convert to ISO format

      // const appointmentDateTime = dayjs(appointmentDate)

      console.log('new appointment time : ', appointmentDateTime);

      // Update the appointmenttime in the form
      this.editForm.get('appointmenttime')?.patchValue(appointmentDateTime);

      // Optionally store the selected time and hoist for reference
      this.selectedTime = timetable.hoisttime;
      this.selectedHoist = timetable.hoistid;
    }
  }

  loadDataFromOtherEntities() {
    this.autocareappointmenttypeService.query().subscribe((res: any) => {
      this.autocareappointmenttypes = res.body;
    });

    // for (let i = 0; i < 31; i++) {
    //   this.customervehicleService.query({ size: 2000, page: i }).subscribe((res: any) => {
    //     if (res.body) {
    //       this.customervehicles = this.customervehicles.concat(res.body); // Append results to the array
    //     }
    //   });
    // }
  }

  filteredVehicles: ICustomervehicle[] = [];

  onVehicleSearch(event: Event): void {
    const input = event.target as HTMLInputElement;
    const searchTerm = input.value;

    if (searchTerm.length > 2) {
      // Use the new service method to fetch matching results
      this.customervehicleService.findByVehicleNumber(searchTerm).subscribe(response => {
        this.filteredVehicles = response.body || [];
      });
    } else {
      // Clear the suggestions if input is too short
      this.filteredVehicles = [];
    }
  }
  // vehicleSelected(event: Event): void {
  //   const input = event.target as HTMLInputElement;
  //   const selectedVehicleNumber = input.value;

  //   // Find the selected vehicle from the filtered list
  //   const selectedVehicle = this.filteredVehicles.find(vehicle => vehicle.vehiclenumber === selectedVehicleNumber);

  //   if (selectedVehicle) {
  //     // Update the form with the selected vehicle's customer details
  //     this.editForm.patchValue({
  //       customername: selectedVehicle.customername,
  //       contactnumber: selectedVehicle.contactnumber,
  //     });
  //   } else {
  //     // Clear the fields if no match is found
  //     this.form.patchValue({
  //       customername: '',
  //       contactnumber: '',
  //     });
  //   }
  // }

  // loadCustomerDetails() {
  //   this.editForm.get(['vehiclenumber'])?.valueChanges.subscribe(vehicleNumber => {
  //     const selectedVehicle = this.customervehicles.find(vehicle => vehicle.vehiclenumber === vehicleNumber);

  //     if (selectedVehicle && selectedVehicle.customerid) {
  //       this.customerService.find(selectedVehicle.customerid).subscribe(customerResponse => {
  //         const customer = customerResponse.body;
  //         if (customer) {
  //           this.editForm.get(['customername'])?.patchValue(customer.fullname);
  //           this.editForm.get(['contactnumber'])?.patchValue(customer.residencephone);
  //         }
  //       });
  //     } else {
  //       this.editForm.get(['customername'])?.patchValue(null);
  //       this.editForm.get(['contactnumber'])?.patchValue(null);
  //     }
  //   });
  // }

  loadHoistAppointmentTime(): void {
    this.autocarehoisttypeService.query().subscribe(response => {
      this.hoistTypeData = response.body || [];
      console.log('Hoist Type : ', this.hoistTypeData);
    });
    this.autocarehoistService.query().subscribe(response => {
      this.hoistData = response.body || [];
      console.log('Hoists : ', this.hoistData);
    });
    this.autocaretimetableService.query({ size: 2000, page: 0 }).subscribe(response => {
      this.timetableData = response.body || [];

      console.log('Time slots : ', this.timetableData);
    });
  }

  // loadHoistAppointmentTime(): void {
  //   forkJoin({
  //     hoistTypes: this.autocarehoisttypeService.query(),
  //     hoists: this.autocarehoistService.query(),
  //     timetables: this.autocaretimetableService.query({ size: 2000, page: 0 }),
  //   }).subscribe(({ hoistTypes, hoists, timetables }) => {
  //     this.hoistTypeData = hoistTypes.body || [];
  //     this.hoistData = hoists.body || [];
  //     this.timetableData = (timetables.body || []).map(timetable => {
  //       if (timetable.hoisttime) {
  //         return { ...timetable, hoisttime: dayjs(timetable.hoisttime).format('HH:mm') };
  //       }
  //       return timetable;
  //     });
  //   });
  // }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const autocareappointment = this.autocareappointmentFormService.getAutocareappointment(this.editForm);
    if (autocareappointment.id !== null) {
      autocareappointment.lmd = dayjs();
      this.subscribeToSaveResponse(this.autocareappointmentService.update(autocareappointment));
    } else {
      autocareappointment.addeddate = dayjs();
      autocareappointment.lmd = dayjs();
      this.subscribeToSaveResponse(this.autocareappointmentService.create(autocareappointment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutocareappointment>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(autocareappointment: IAutocareappointment): void {
    this.autocareappointment = autocareappointment;
    this.autocareappointmentFormService.resetForm(this.editForm, autocareappointment);
  }
}
