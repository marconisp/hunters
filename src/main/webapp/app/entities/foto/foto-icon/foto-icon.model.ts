export interface IFotoIcon {
  id?: number;
  conteudoContentType?: string;
  conteudo?: string;
}

export class FotoIcon implements IFotoIcon {
  constructor(public id?: number, public conteudoContentType?: string, public conteudo?: string) {}
}

export function getFotoIconIdentifier(fotoIcon: IFotoIcon): number | undefined {
  return fotoIcon.id;
}
