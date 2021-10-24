import { IBookings } from 'app/shared/model/bookings.model';
import { IRoom } from 'app/shared/model/room.model';

export interface IRoomsInBooking {
  id?: number;
  bookings?: IBookings | null;
  room?: IRoom | null;
}

export const defaultValue: Readonly<IRoomsInBooking> = {};
