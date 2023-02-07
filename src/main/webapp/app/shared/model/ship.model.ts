import { ICountry } from 'app/shared/model/country.model';
import { IFlag } from 'app/shared/model/flag.model';

export interface IShip {
  id?: number;
  name?: string;
  classificationSociety?: string | null;
  iceClassPolarCode?: string | null;
  technicalEfficiencyCode?: string | null;
  shipType?: string | null;
  ownerCountry?: ICountry | null;
  flag?: IFlag | null;
}

export const defaultValue: Readonly<IShip> = {};
