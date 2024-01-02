import { IMessage } from 'app/shared/model/message.model';
import { IVykyUser } from 'app/shared/model/vyky-user.model';

export interface IMessageRecipientAttrs {
  id?: number;
  isRead?: boolean | null;
  isDeleted?: boolean | null;
  message?: IMessage | null;
  recipient?: IVykyUser | null;
}

export const defaultValue: Readonly<IMessageRecipientAttrs> = {
  isRead: false,
  isDeleted: false,
};
