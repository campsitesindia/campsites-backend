import dayjs from 'dayjs';
import { IListing } from 'app/shared/model/listing.model';

export interface IPhotos {
  id?: number;
  alt?: string | null;
  caption?: string | null;
  description?: string | null;
  href?: string | null;
  src?: string | null;
  title?: string | null;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
  listing?: IListing | null;
}

export const defaultValue: Readonly<IPhotos> = {};
