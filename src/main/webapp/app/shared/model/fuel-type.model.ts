import { FuelTypeCode } from 'app/shared/model/enumerations/fuel-type-code.model';

export interface IFuelType {
  id?: number;
  name?: string;
  fuelTypeCode?: FuelTypeCode;
  carbonFactory?: number;
}

export const defaultValue: Readonly<IFuelType> = {};
