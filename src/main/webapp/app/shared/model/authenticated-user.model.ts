import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { AuthProvider } from 'app/shared/model/enumerations/auth-provider.model';

export interface IAuthenticatedUser {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  provider?: AuthProvider | null;
  authTimestamp?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IAuthenticatedUser> = {};
