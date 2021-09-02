import dayjs from 'dayjs';
import { IRoom } from 'app/shared/model/room.model';

export interface IRoomFeatures {
  id?: number;
  title?: string | null;
  count?: number | null;
  thumbnail?: string | null;
  icon?: string | null;
  color?: string | null;
  imgIcon?: string | null;
  description?: string | null;
  parent?: number | null;
  taxonomy?: string | null;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
  room?: IRoom | null;
}

export const defaultValue: Readonly<IRoomFeatures> = {};
