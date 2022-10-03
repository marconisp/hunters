import { IPagar } from 'app/entities/controle/pagar/pagar.model';

export interface IFotoPagar {
  id?: number;
  conteudoContentType?: string;
  conteudo?: string;
  pagar?: IPagar | null;
}

export class FotoPagar implements IFotoPagar {
  constructor(public id?: number, public conteudoContentType?: string, public conteudo?: string, public pagar?: IPagar | null) {}
}

export function getFotoPagarIdentifier(fotoPagar: IFotoPagar): number | undefined {
  return fotoPagar.id;
}
