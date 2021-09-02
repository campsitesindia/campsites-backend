import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ILike } from 'app/shared/model/like.model';
import { IImages } from 'app/shared/model/images.model';
import { IComments } from 'app/shared/model/comments.model';

export interface IPost {
  id?: number;
  content?: string | null;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
  user?: IUser | null;
  like?: ILike | null;
  images?: IImages[] | null;
  comments?: IComments[] | null;
}

export const defaultValue: Readonly<IPost> = {};
