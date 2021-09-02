import dayjs from 'dayjs';
import { IListing } from 'app/shared/model/listing.model';

export interface IVideos {
  id?: number;
  name?: string | null;
  url?: string | null;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
  listing?: IListing | null;
}

export const defaultValue: Readonly<IVideos> = {};
