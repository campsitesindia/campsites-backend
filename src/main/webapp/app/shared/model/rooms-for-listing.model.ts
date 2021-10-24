import { IListing } from 'app/shared/model/listing.model';
import { IRoom } from 'app/shared/model/room.model';

export interface IRoomsForListing {
  id?: number;
  listing?: IListing | null;
  room?: IRoom | null;
}

export const defaultValue: Readonly<IRoomsForListing> = {};
