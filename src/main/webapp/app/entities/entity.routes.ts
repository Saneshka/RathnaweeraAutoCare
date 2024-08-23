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
  {
    path: 'autocareappointment',
    data: { pageTitle: 'Autocareappointments' },
    loadChildren: () => import('./autocareappointment/autocareappointment.routes'),
  },
  {
    path: 'autocarejobinimages',
    data: { pageTitle: 'Autocarejobinimages' },
    loadChildren: () => import('./autocarejobinimages/autocarejobinimages.routes'),
  },
  {
    path: 'autocareappointmenttype',
    data: { pageTitle: 'Autocareappointmenttypes' },
    loadChildren: () => import('./autocareappointmenttype/autocareappointmenttype.routes'),
  },
  {
    path: 'vehiclecategory',
    data: { pageTitle: 'Vehiclecategories' },
    loadChildren: () => import('./vehiclecategory/vehiclecategory.routes'),
  },
  {
    path: 'inventory',
    data: { pageTitle: 'Inventories' },
    loadChildren: () => import('./inventory/inventory.routes'),
  },
  {
    path: 'servicesubcategory',
    data: { pageTitle: 'Servicesubcategories' },
    loadChildren: () => import('./servicesubcategory/servicesubcategory.routes'),
  },
  {
    path: 'stocklocations',
    data: { pageTitle: 'Stocklocations' },
    loadChildren: () => import('./stocklocations/stocklocations.routes'),
  },
  {
    path: 'inventorybatches',
    data: { pageTitle: 'Inventorybatches' },
    loadChildren: () => import('./inventorybatches/inventorybatches.routes'),
  },
  {
    path: 'vehiclebrandname',
    data: { pageTitle: 'Vehiclebrandnames' },
    loadChildren: () => import('./vehiclebrandname/vehiclebrandname.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
