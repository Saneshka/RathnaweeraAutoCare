import dayjs from 'dayjs/esm';

import { IAutocareappointment, NewAutocareappointment } from './autocareappointment.model';

export const sampleWithRequiredData: IAutocareappointment = {
  id: 31381,
};

export const sampleWithPartialData: IAutocareappointment = {
  id: 22091,
  addeddate: dayjs('2024-08-19T16:45'),
  conformdate: dayjs('2024-08-19T19:12'),
  appointmentnumber: 15605,
  isconformed: true,
  contactnumber: 'repurpose um via',
  issued: true,
  isarrived: false,
  isnoanswer: false,
  missedappointmentcall: 'modest',
  customermobilevehicleid: 16588,
  vehicleid: 9851,
};

export const sampleWithFullData: IAutocareappointment = {
  id: 17698,
  appointmenttype: 8842,
  appointmentdate: dayjs('2024-08-20T06:06'),
  addeddate: dayjs('2024-08-20T03:04'),
  conformdate: dayjs('2024-08-20T05:57'),
  appointmentnumber: 32624,
  vehiclenumber: 'woot thoroughly',
  appointmenttime: dayjs('2024-08-20T05:41'),
  isconformed: true,
  conformedby: 20418,
  lmd: dayjs('2024-08-19T10:24'),
  lmu: 28968,
  customerid: 8226,
  contactnumber: 'versus boohoo colloquy',
  customername: 'refill while',
  issued: true,
  hoistid: 13222,
  isarrived: false,
  iscancel: false,
  isnoanswer: false,
  missedappointmentcall: 'untrue kowtow when',
  customermobileid: 23070,
  customermobilevehicleid: 4459,
  vehicleid: 19534,
  ismobileappointment: false,
  advancepayment: 7479.75,
  jobid: 31361,
};

export const sampleWithNewData: NewAutocareappointment = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
