import dayjs from 'dayjs';
import { IListingType } from 'app/shared/model/listing-type.model';
import { IRating } from 'app/shared/model/rating.model';
import { ILocation } from 'app/shared/model/location.model';
import { IFeatures } from 'app/shared/model/features.model';
import { IRoom } from 'app/shared/model/room.model';
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
  phone?: string | null;
  email?: string | null;
  website?: string | null;
  comment?: boolean | null;
  disableBooking?: boolean | null;
  viewCount?: number | null;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
  listingType?: IListingType | null;
  rating?: IRating | null;
  location?: ILocation | null;
  feature?: IFeatures | null;
  room?: IRoom | null;
  owner?: IUser | null;
}

export const defaultValue: Readonly<IListing> = {
  isFeatured: false,
  comment: false,
  disableBooking: false,
};
