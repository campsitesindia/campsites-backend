import { IListing } from 'app/shared/model/listing.model';

export interface IRating {
  id?: number;
  value?: number | null;
  name?: string | null;
  listing?: IListing | null;
}

export const defaultValue: Readonly<IRating> = {};
