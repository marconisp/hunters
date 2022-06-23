export interface IFoto {
  id?: number;
  conteudoContentType?: string;
  conteudo?: string;
}

export class Foto implements IFoto {
  constructor(public id?: number, public conteudoContentType?: string, public conteudo?: string) {}
}

export function getFotoIdentifier(foto: IFoto): number | undefined {
  return foto.id;
}
