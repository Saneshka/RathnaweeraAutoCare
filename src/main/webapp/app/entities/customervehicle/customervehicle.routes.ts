import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CustomervehicleComponent } from './list/customervehicle.component';
import { CustomervehicleDetailComponent } from './detail/customervehicle-detail.component';
import { CustomervehicleUpdateComponent } from './update/customervehicle-update.component';
import CustomervehicleResolve from './route/customervehicle-routing-resolve.service';

const customervehicleRoute: Routes = [
  {
    path: '',
    component: CustomervehicleComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomervehicleDetailComponent,
    resolve: {
      customervehicle: CustomervehicleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomervehicleUpdateComponent,
    resolve: {
      customervehicle: CustomervehicleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomervehicleUpdateComponent,
    resolve: {
      customervehicle: CustomervehicleResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default customervehicleRoute;
