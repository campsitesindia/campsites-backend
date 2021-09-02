import dayjs from 'dayjs';
import { IPost } from 'app/shared/model/post.model';
import { IUser } from 'app/shared/model/user.model';

export interface IComments {
  id?: number;
  commentText?: string | null;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
  post?: IPost | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IComments> = {};
