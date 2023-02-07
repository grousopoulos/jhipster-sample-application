import { IFuelType } from 'app/shared/model/fuel-type.model';
import { UnitOfMeasure } from 'app/shared/model/enumerations/unit-of-measure.model';

export interface IEventReportLine {
  id?: number;
  quantity?: number | null;
  unitOfMeasure?: UnitOfMeasure | null;
  fuelType?: IFuelType | null;
}

export const defaultValue: Readonly<IEventReportLine> = {};
