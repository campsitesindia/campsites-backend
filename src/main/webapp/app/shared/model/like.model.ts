import dayjs from 'dayjs';
import { IPost } from 'app/shared/model/post.model';
import { IUser } from 'app/shared/model/user.model';
import { IImages } from 'app/shared/model/images.model';

export interface ILike {
  id?: number;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
  post?: IPost | null;
  user?: IUser | null;
  images?: IImages | null;
}

export const defaultValue: Readonly<ILike> = {};
