import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'Authorities' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'customer',
    data: { pageTitle: 'Customers' },
    loadChildren: () => import('./customer/customer.routes'),
  },
  {
    path: 'customervehicle',
    data: { pageTitle: 'Customervehicles' },
    loadChildren: () => import('./customervehicle/customervehicle.routes'),
  },
  {
    path: 'servicecategory',
    data: { pageTitle: 'Servicecategories' },
    loadChildren: () => import('./servicecategory/servicecategory.routes'),
  },
  {
    path: 'autocarejob',
    data: { pageTitle: 'Autocarejobs' },
    loadChildren: () => import('./autocarejob/autocarejob.routes'),
  },
  {
    path: 'vehicletype',
    data: { pageTitle: 'Vehicletypes' },
    loadChildren: () => import('./vehicletype/vehicletype.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
