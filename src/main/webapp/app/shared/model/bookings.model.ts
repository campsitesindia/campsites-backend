import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IListing } from 'app/shared/model/listing.model';
import { IInvoice } from 'app/shared/model/invoice.model';

export interface IBookings {
  id?: number;
  name?: string | null;
  checkInDate?: string | null;
  checkOutDate?: string | null;
  pricePerNight?: number | null;
  childPricePerNight?: number | null;
  numOfNights?: number | null;
  razorpayPaymentId?: string | null;
  razorpayOrderId?: string | null;
  razorpaySignature?: string | null;
  discount?: number | null;
  totalAmount?: number | null;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
  user?: IUser | null;
  listing?: IListing | null;
  invoices?: IInvoice[] | null;
}

export const defaultValue: Readonly<IBookings> = {};
