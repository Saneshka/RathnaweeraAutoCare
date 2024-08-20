import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AutocareappointmentComponent } from './list/autocareappointment.component';
import { AutocareappointmentDetailComponent } from './detail/autocareappointment-detail.component';
import { AutocareappointmentUpdateComponent } from './update/autocareappointment-update.component';
import AutocareappointmentResolve from './route/autocareappointment-routing-resolve.service';

const autocareappointmentRoute: Routes = [
  {
    path: '',
    component: AutocareappointmentComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AutocareappointmentDetailComponent,
    resolve: {
      autocareappointment: AutocareappointmentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AutocareappointmentUpdateComponent,
    resolve: {
      autocareappointment: AutocareappointmentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AutocareappointmentUpdateComponent,
    resolve: {
      autocareappointment: AutocareappointmentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default autocareappointmentRoute;
