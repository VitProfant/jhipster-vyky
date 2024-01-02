import dayjs from 'dayjs';
import { IVykyUser } from 'app/shared/model/vyky-user.model';

export interface IMessage {
  id?: number;
  content?: string;
  createdAt?: dayjs.Dayjs;
  deletedAt?: dayjs.Dayjs | null;
  deletedByAuthor?: boolean | null;
  parent?: IMessage | null;
  author?: IVykyUser | null;
}

export const defaultValue: Readonly<IMessage> = {
  deletedByAuthor: false,
};
