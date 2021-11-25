import dayjs from 'dayjs';
import { IAlbum } from 'app/shared/model/album.model';
import { IListing } from 'app/shared/model/listing.model';
import { ITag } from 'app/shared/model/tag.model';

export interface IPhotos {
  id?: number;
  alt?: string | null;
  caption?: string | null;
  description?: string | null;
  href?: string | null;
  src?: string | null;
  title?: string | null;
  imageContentType?: string;
  image?: string;
  isCoverImage?: boolean | null;
  height?: number | null;
  width?: number | null;
  taken?: string | null;
  uploaded?: string | null;
  createdBy?: string | null;
  createdDate?: string | null;
  updatedBy?: string | null;
  updateDate?: string | null;
  album?: IAlbum | null;
  listing?: IListing | null;
  tags?: ITag[] | null;
}

export const defaultValue: Readonly<IPhotos> = {
  isCoverImage: false,
};
