import { Component, inject, OnInit, ViewEncapsulation } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
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

@Component({
  standalone: true,
  selector: 'jhi-autocareappointment-update',
  templateUrl: './autocareappointment-update.component.html',
  encapsulation: ViewEncapsulation.None,
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
  styles: [
    `
      // .time-button {
      //   padding: 10px;
      //   margin: 5px;
      //   border: 2px solid transparent;
      //   background-color: white;
      //   color: black;
      //   cursor: pointer;
      // }

      // .time-button.selected {
      //   background-color: darkgray;
      //   border-color: red;
      //   color: red;
      // }

      table {
        width: 100%;
        border-collapse: collapse;
        text-align: center;
      }

      th,
      td {
        padding: 10px;
        border: 1px solid #ccc;
      }

      th {
        background-color: #6fb1ff;
        color: white;
        font-weight: bold;
      }

      .time-button {
        display: block;
        margin: 5px;
        width: 100%;
        padding: 10px;
        background-color: #f0f0f0;
        border: 1px solid #ccc;
        cursor: pointer;
      }

      .time-button.selected {
        background-color: #333; /* Darker background */
        border: 2px solid red; /* Red border */
        color: white;
      }

      .time-button:hover {
        background-color: #0056b3;
        color: white;
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

  hoists: { id: number; name: string; times: string[] }[] = [];

  protected autocareappointmentService = inject(AutocareappointmentService);
  protected autocareappointmentFormService = inject(AutocareappointmentFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected autocareappointmenttypeService = inject(AutocareappointmenttypeService);
  protected customervehicleService = inject(CustomervehicleService);
  protected customerService = inject(CustomerService);
  protected autocarehoistService = inject(AutocarehoistService);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutocareappointmentFormGroup = this.autocareappointmentFormService.createAutocareappointmentFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autocareappointment }) => {
      this.autocareappointment = autocareappointment;
      if (autocareappointment) {
        this.updateForm(autocareappointment);
      }
      this.loadDataFromOtherEntities();
      this.loadCustomerDetails();
      this.loadHoists();
    });
  }

  onTimeClick(hoistId: number, time: string): void {
    this.selectedTime = time;
    this.selectedHoist = hoistId;

    console.log('Selected hoist ID:', this.selectedHoist);
    console.log('Selected time:', this.selectedTime);
    // Update the hoist ID in the form
    this.editForm.get('hoistid')?.patchValue(hoistId);

    // Get the current appointment date or default to today's date
    const appointmentDate = this.editForm.get('appointmentdate')?.value || dayjs();

    // Combine the appointment date with the selected time
    const [hour, minute] = time.split(':');
    const fullDateTime = dayjs(appointmentDate).set('hour', parseInt(hour, 10)).set('minute', parseInt(minute, 10));

    // Format the date and time as a local string (without converting to UTC)
    const formattedDateTime = fullDateTime.format('YYYY-MM-DDTHH:mm:ss');

    // Update the appointment time in the form with the formatted local time
    this.editForm.get('appointmenttime')?.patchValue(formattedDateTime);
  }

  loadDataFromOtherEntities() {
    this.autocareappointmenttypeService.query().subscribe((res: any) => {
      this.autocareappointmenttypes = res.body;
    });

    for (let i = 0; i < 31; i++) {
      this.customervehicleService.query({ size: 2000, page: i }).subscribe((res: any) => {
        if (res.body) {
          this.customervehicles = this.customervehicles.concat(res.body); // Append results to the array
        }
      });
    }
  }

  loadCustomerDetails() {
    this.editForm.get(['vehiclenumber'])?.valueChanges.subscribe(vehicleNumber => {
      const selectedVehicle = this.customervehicles.find(vehicle => vehicle.vehiclenumber === vehicleNumber);

      if (selectedVehicle && selectedVehicle.customerid) {
        // Fetch the customer details using the customerid from the selected vehicle
        this.customerService.find(selectedVehicle.customerid).subscribe(customerResponse => {
          const customer = customerResponse.body; // Assuming the response structure has the customer details in the body

          if (customer) {
            // Patch the customername field with the name of the customer
            this.editForm.get(['customername'])?.patchValue(customer.fullname);
            this.editForm.get(['contactnumber'])?.patchValue(customer.residencephone);
          }
        });
      } else {
        // If no vehicle or customer found, clear the customer name field
        this.editForm.get(['customername'])?.patchValue(null);
        this.editForm.get(['contactnumber'])?.patchValue(null);
      }
    });
  }

  loadHoists(): void {
    this.autocarehoistService.query().subscribe(response => {
      const hoistData = response.body || [];

      // Map the fetched hoist data to the structure required
      this.hoists = hoistData.map(hoist => {
        const hoistType = this.getHoistType(hoist.id); // You might determine type based on the hoist ID or other fields
        const times = this.generateTimeSlots(hoistType);

        return {
          id: hoist.id,
          name: hoistType,
          times: times,
        };
      });
    });
  }

  getHoistType(hoistId: number): string {
    // Determine hoist type based on hoistId or other criteria
    // Example logic: you can adjust this as per your data source
    if ([1, 2].includes(hoistId)) {
      return 'Double Light';
    } else if ([3, 4, 5].includes(hoistId)) {
      return 'Light';
    } else {
      return 'Heavy';
    }
  }

  generateTimeSlots(hoistType: string): string[] {
    let timeSlots: string[] = [];
    let startTime = dayjs('2024-01-01T08:00:00'); // Start from 08:00 AM
    let endTime;

    if (hoistType === 'Heavy') {
      // Heavy hoists (Gap of 1 hour and 30 minutes, end at 02:30 PM)
      endTime = dayjs('2024-01-01T14:30:00');
      while (startTime.isBefore(endTime)) {
        timeSlots.push(startTime.format('hh:mm A'));
        startTime = startTime.add(1, 'hour').add(30, 'minutes');
      }
    } else if (hoistType === 'Light') {
      // Light hoists (Gap of 45 minutes, end at 05:00 PM)
      endTime = dayjs('2024-01-01T17:00:00');
      while (startTime.isBefore(endTime)) {
        timeSlots.push(startTime.format('hh:mm A'));
        startTime = startTime.add(45, 'minutes');
      }
    } else if (hoistType === 'Double Light') {
      // Double Light hoists (Gap of 45 minutes, but double the slots)
      endTime = dayjs('2024-01-01T17:00:00');
      while (startTime.isBefore(endTime)) {
        const slot = startTime.format('hh:mm A');
        timeSlots.push(slot, slot); // Push twice for double capacity
        startTime = startTime.add(45, 'minutes');
      }
    }

    return timeSlots;
  }

  previousState(): void {
    window.history.back();
  }

  // save(): void {
  //   this.isSaving = true;
  //   const autocareappointment = this.autocareappointmentFormService.getAutocareappointment(this.editForm);
  //   if (autocareappointment.id !== null) {
  //     autocareappointment.lmd = dayjs();
  //     this.subscribeToSaveResponse(this.autocareappointmentService.update(autocareappointment));
  //   } else {
  //     autocareappointment.addeddate = dayjs();
  //     autocareappointment.lmd = dayjs();
  //     this.subscribeToSaveResponse(this.autocareappointmentService.create(autocareappointment));
  //   }
  // }

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
