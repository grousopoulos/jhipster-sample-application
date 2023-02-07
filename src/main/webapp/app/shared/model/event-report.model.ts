import dayjs from 'dayjs';
import { IVoyage } from 'app/shared/model/voyage.model';

export interface IEventReport {
  id?: number;
  documentDateAndTime?: string;
  speedGps?: number | null;
  documentDisplayNumber?: string | null;
  voyage?: IVoyage | null;
}

export const defaultValue: Readonly<IEventReport> = {};
