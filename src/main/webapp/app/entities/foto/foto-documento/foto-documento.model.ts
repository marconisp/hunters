import { IDocumento } from 'app/entities/user/documento/documento.model';

export interface IFotoDocumento {
  id?: number;
  conteudoContentType?: string;
  conteudo?: string;
  documento?: IDocumento | null;
}

export class FotoDocumento implements IFotoDocumento {
  constructor(public id?: number, public conteudoContentType?: string, public conteudo?: string, public documento?: IDocumento | null) {}
}

export function getFotoDocumentoIdentifier(fotoDocumento: IFotoDocumento): number | undefined {
  return fotoDocumento.id;
}
