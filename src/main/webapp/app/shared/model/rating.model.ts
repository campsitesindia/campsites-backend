import { IListing } from 'app/shared/model/listing.model';

export interface IRating {
  id?: number;
  name?: string | null;
  listing?: IListing | null;
}

export const defaultValue: Readonly<IRating> = {};
