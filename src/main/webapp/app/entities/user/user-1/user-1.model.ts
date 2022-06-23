import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';

export interface IUser1 {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  dadosPessoais?: IDadosPessoais[] | null;
}

export class User1 implements IUser1 {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public dadosPessoais?: IDadosPessoais[] | null
  ) {}
}

export function getUser1Identifier(user1: IUser1): number | undefined {
  return user1.id;
}
