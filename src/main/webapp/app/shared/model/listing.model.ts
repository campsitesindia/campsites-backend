import dayjs from 'dayjs';
import { IListingType } from 'app/shared/model/listing-type.model';
import { ILocation } from 'app/shared/model/location.model';
import { IUser } from 'app/shared/model/user.model';

export interface IListing {
  id?: number;
  address?: string | null;
  latitude?: number | null;
  longitude?: number | null;
  url?: string | null;
  title?: string | null;
  content?: string | null;
  thumbnail?: string | null;
  isFeatured?: boolean | null;
  pricePerPerson?: number | null;
  phone?: string | null;
  email?: string | null;
  website?: string | null;
  comment?: string | null;
  disableBooking?: boolean | null;
  viewCount?: number | null;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
  listingType?: IListingType | null;
  location?: ILocation | null;
  owner?: IUser | null;
}

export const defaultValue: Readonly<IListing> = {
  isFeatured: false,
  disableBooking: false,
};
