import { ITipoDocumento } from 'app/entities/config/tipo-documento/tipo-documento.model';
import { IFotoDocumento } from 'app/entities/foto/foto-documento/foto-documento.model';
import { IDadosPessoais } from 'app/entities/user/dados-pessoais/dados-pessoais.model';

export interface IDocumento {
  id?: number;
  descricao?: string;
  tipoDocumento?: ITipoDocumento | null;
  fotos?: IFotoDocumento[] | null;
  dadosPessoais?: IDadosPessoais | null;
}

export class Documento implements IDocumento {
  constructor(
    public id?: number,
    public descricao?: string,
    public tipoDocumento?: ITipoDocumento | null,
    public fotos?: IFotoDocumento[] | null,
    public dadosPessoais?: IDadosPessoais | null
  ) {}
}

export function getDocumentoIdentifier(documento: IDocumento): number | undefined {
  return documento.id;
}
