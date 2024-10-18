import { Component, inject, OnInit } from '@angular/core';
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

@Component({
  standalone: true,
  selector: 'jhi-autocareappointment-update',
  templateUrl: './autocareappointment-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AutocareappointmentUpdateComponent implements OnInit {
  isSaving = false;
  autocareappointment: IAutocareappointment | null = null;
  autocareappointmenttypes: IAutocareappointmenttype[] = [];
  customervehicles: ICustomervehicle[] = [];
  customerDetails: any | null = null;

  protected autocareappointmentService = inject(AutocareappointmentService);
  protected autocareappointmentFormService = inject(AutocareappointmentFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected autocareappointmenttypeService = inject(AutocareappointmenttypeService);
  protected customervehicleService = inject(CustomervehicleService);
  protected customerService = inject(CustomerService);

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
    });
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

  // loadCustomerDetails(id: any) {
  //   this.customerDetails = this.customerService.find(id);
  //   console.log(this.customerDetails)
  //   // if (this.customerDetails) {
  //   //   this.editForm.get(['customername'])?.patchValue(this.customerDetails.n)
  //   // }
  // }

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
