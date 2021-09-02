import dayjs from 'dayjs';

export interface IRoomType {
  id?: number;
  description?: string | null;
  maxCapacity?: string | null;
  numberOfBeds?: number | null;
  numberOfBathrooms?: number | null;
  roomRatePerNigt?: number | null;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
}

export const defaultValue: Readonly<IRoomType> = {};
