import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';

export interface ICaracteristicasPsiquicas {
  id?: number;
  nome?: string;
  dadosPessoais?: IDadosPessoais | null;
}

export class CaracteristicasPsiquicas implements ICaracteristicasPsiquicas {
  constructor(public id?: number, public nome?: string, public dadosPessoais?: IDadosPessoais | null) {}
}

export function getCaracteristicasPsiquicasIdentifier(caracteristicasPsiquicas: ICaracteristicasPsiquicas): number | undefined {
  return caracteristicasPsiquicas.id;
}
