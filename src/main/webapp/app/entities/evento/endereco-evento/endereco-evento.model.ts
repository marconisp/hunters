import { IEvento } from 'app/entities/evento/evento/evento.model';

export interface IEnderecoEvento {
  id?: number;
  cep?: string;
  logradouro?: string;
  complemento?: string | null;
  numero?: string;
  bairro?: string;
  cidade?: string;
  uf?: string;
  evento?: IEvento | null;
}

export class EnderecoEvento implements IEnderecoEvento {
  constructor(
    public id?: number,
    public cep?: string,
    public logradouro?: string,
    public complemento?: string | null,
    public numero?: string,
    public bairro?: string,
    public cidade?: string,
    public uf?: string,
    public evento?: IEvento | null
  ) {}
}

export function getEnderecoEventoIdentifier(enderecoEvento: IEnderecoEvento): number | undefined {
  return enderecoEvento.id;
}
