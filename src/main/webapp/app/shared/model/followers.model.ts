import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IFollowers {
  id?: number;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
  followedBy?: IUser | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IFollowers> = {};
