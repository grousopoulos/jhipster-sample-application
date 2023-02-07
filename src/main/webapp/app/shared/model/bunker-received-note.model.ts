import dayjs from 'dayjs';
import { IVoyage } from 'app/shared/model/voyage.model';

export interface IBunkerReceivedNote {
  id?: number;
  documentDateAndTime?: string;
  documentDisplayNumber?: string | null;
  voyage?: IVoyage | null;
}

export const defaultValue: Readonly<IBunkerReceivedNote> = {};
