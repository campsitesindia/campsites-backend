import dayjs from 'dayjs';
import { IBookings } from 'app/shared/model/bookings.model';
import { IUser } from 'app/shared/model/user.model';
import { InvoiceStatus } from 'app/shared/model/enumerations/invoice-status.model';

export interface IInvoice {
  id?: number;
  invoiceAmount?: number | null;
  status?: InvoiceStatus | null;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
  bookings?: IBookings | null;
  customer?: IUser | null;
}

export const defaultValue: Readonly<IInvoice> = {};
