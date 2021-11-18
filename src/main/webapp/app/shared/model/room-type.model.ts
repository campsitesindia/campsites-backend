import dayjs from 'dayjs';

export interface IRoomType {
  id?: number;
  type?: string | null;
  description?: string | null;
  maxCapacity?: string | null;
  numberOfBeds?: number | null;
  numberOfBathrooms?: number | null;
  roomRatePerNight?: number | null;
  roomRateChildPerNight?: number | null;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
}

export const defaultValue: Readonly<IRoomType> = {};
