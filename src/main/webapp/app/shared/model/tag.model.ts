import { IPhotos } from 'app/shared/model/photos.model';

export interface ITag {
  id?: number;
  name?: string;
  photos?: IPhotos[] | null;
}

export const defaultValue: Readonly<ITag> = {};
