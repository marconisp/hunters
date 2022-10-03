import { IReceber } from 'app/entities/controle/receber/receber.model';

export interface IFotoReceber {
  id?: number;
  conteudoContentType?: string;
  conteudo?: string;
  receber?: IReceber | null;
}

export class FotoReceber implements IFotoReceber {
  constructor(public id?: number, public conteudoContentType?: string, public conteudo?: string, public receber?: IReceber | null) {}
}

export function getFotoReceberIdentifier(fotoReceber: IFotoReceber): number | undefined {
  return fotoReceber.id;
}
