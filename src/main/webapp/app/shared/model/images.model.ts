import dayjs from 'dayjs';
import { IPost } from 'app/shared/model/post.model';
import { IUser } from 'app/shared/model/user.model';
import { ILike } from 'app/shared/model/like.model';

export interface IImages {
  id?: number;
  imageUrl?: string | null;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
  post?: IPost | null;
  user?: IUser | null;
  like?: ILike | null;
}

export const defaultValue: Readonly<IImages> = {};
