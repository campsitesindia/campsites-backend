import dayjs from 'dayjs';
import { IRoomType } from 'app/shared/model/room-type.model';

export interface IRoom {
  id?: number;
  name?: string | null;
  roomNumber?: string | null;
  isSmoking?: string | null;
  status?: string | null;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
  roomType?: IRoomType | null;
}

export const defaultValue: Readonly<IRoom> = {};
