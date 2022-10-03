import { IColaborador } from 'app/entities/user/colaborador/colaborador.model';

export interface ITipoContratacao {
  id?: number;
  nome?: string;
  colaborador?: IColaborador | null;
}

export class TipoContratacao implements ITipoContratacao {
  constructor(public id?: number, public nome?: string, public colaborador?: IColaborador | null) {}
}

export function getTipoContratacaoIdentifier(tipoContratacao: ITipoContratacao): number | undefined {
  return tipoContratacao.id;
}
