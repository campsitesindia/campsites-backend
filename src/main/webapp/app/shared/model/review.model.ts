import dayjs from 'dayjs';
import { IBookings } from 'app/shared/model/bookings.model';

export interface IReview {
  id?: number;
  rating?: number | null;
  reviewbBody?: string | null;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
  booking?: IBookings | null;
}

export const defaultValue: Readonly<IReview> = {};
